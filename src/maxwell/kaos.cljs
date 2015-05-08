(ns maxwell.kaos
  "Handles error normalization and error watching"
  (:require [clojure.string :as s]
            [clojure.set :as set])
  (:import [goog debug]))

;; Note: goog has two error types, those from window.onerror and
;; those from try-catch

;; window.onerror returns
;; #js {message: message,
;;	fileName: url,
;;	line: line,
;;	col: opt_col,
;;	error: opt_error} 
;; Since opt_error might be the whole thing, if present, it should be used 
;; and a regular catch normalized with .normalizeErrorObject

;; try-catch
;; #js {'message': err,
;; 	'name': 'Unknown error',
;; 	'lineNumber': 'Not available',
;; 	'fileName': href,
;; 	'stack': 'Not available'}

(def ^:private debug goog.debug)

(defn- normalize
  "Takes a raw error and normalizes it with goog.debug"
  [e]
  (.normalizeErrorObject debug e))

(defn normalized?
  "Checks if the error has been normalized by goog"
  [e]
  (not (undefined? (aget e "fileName"))))

(defn ->map
  "Takes an error and returns a serializable cljs map representing the error with fields:
  - :message String 
  - :stack [String]
  - :file-name String - URL
  - :line-number Int"
  [k]
  (-> (->> (js->clj (if (normalized? k) k (normalize k)))
        (map (fn [[k v]] {(keyword k) v}))
        (reduce merge))
    (update :stack #(s/split % #"\n"))
    (dissoc :name)
    (set/rename-keys {:fileName :file-name :lineNumber :line-number
                      :line :line-number :col :column-number})))

;; The DOM has state, we might as well represent it.
;; cover-names remembers the watches that we have already used to
;; avoid installing the same watch each time on reload.
(defonce ^:private cover-names (atom #{}))

(defn watch-errors!
  "(watch-errors! watcher-name f opts)
  Registers an error catcher f that takes a normalized error object. 
  opts: - :silence? bool. if true the errors will not hit the console
        - :target #js [Obj]. if present it will replace window, i.e. target.onerror = catcher;
  Ex: (watch-errors! :sigfried
        (fn [e] (println \"Zis is Kaos! We don't shush here!\"))
        {:silence? true})"
  ([cover-name catcher] (watch-errors! cover-name catcher
                          {:silence? false :target nil}))
  ([cover-name catcher opts]
   {:pre [(ifn? catcher) (map? opts)]}
   (when-not (contains? @cover-names cover-name) 
     (swap! cover-names #(conj % cover-name))
     (.catchErrors debug #(catcher (->map %))
       (:silence? opts) (:target opts)))))
