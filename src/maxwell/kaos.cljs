(ns maxwell.kaos
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [clojure.string :as s]
            [cljs.core.async :as async])
  (:import [goog debug]))

;; Stack Traces
;; ============

;; Kaos objs are browser-normalized errors

(def debug goog.debug)

(defn error->kaos [e]
  (.normalizeErrorObject debug e))

(defn kaos->map
  "Returns a clj representing the error. Fully serializable"
  [k]
  (update (->> (js->clj k)
            (map (fn [[k v]] {(keyword k) v}))
            (reduce merge))
    :stack
    #(s/split % #"\n")))

(defonce cover-names (atom #{}))

(defn spy!
  "(spy! cover-name f opts)
  Registers an error catcher f that takes a normalized error object.
  opts: - :silence? bool. if true the errors will not hit the console
        - :target #js [Obj]. if present it will replace window, i.e. target.onerror = catcher; 
  Ex: (spy! ::printer (fn [e] (println \"I'm an error\")) {:silence? true})"
  ([cover-name catcher] (spy! cover-name catcher {:silence? false :target nil}))
  ([cover-name catcher opts]
   {:pre [(ifn? catcher) (map? opts)]}
   (when-not (contains? @cover-names cover-name) 
     (swap! cover-names #(conj % cover-name))
     (.catchErrors debug catcher (:silence? opts) (:target opts)))))
