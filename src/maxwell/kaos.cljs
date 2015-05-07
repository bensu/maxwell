(ns maxwell.kaos)

;; Stack Traces
;; ============

;; TODO: Implement a cross-browser stack serialization method or
;; http://google.github.io/closure-library/api/namespace_goog_debug.html

;; cljs current Closure Library Version has a very small Error object.

(def testing goog.testing)

(defn e->str [e]
  nil)

(defn e->map [e]
  {:msg (pr-str (.-message e))
   :stack (pr-str (.-stack e))})
