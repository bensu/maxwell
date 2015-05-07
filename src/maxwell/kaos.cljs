(ns maxwell.kaos
  (:require [clojure.string :as s])
  (:import [goog debug]))

;; Stack Traces
;; ============

;; TODO: Implement a cross-browser stack serialization method or
;; http://google.github.io/closure-library/api/namespace_goog_debug.html

;; cljs current Closure Library Version has a very small Error object.

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

(defn throw-error [msg]
  (js/Error. msg))
