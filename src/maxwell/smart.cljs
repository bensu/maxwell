(ns ^:figwheel-always maxwell.smart)

(enable-console-print!)

;; Browser
;; =======

(def browser (.-browser goog.labs.userAgent))

(defn android-browser? []
  (.isAndroidBrowser browser))

(defn chrome? []
  (.isChrome browser))

(defn coast? []
  (.isCoast browser))

(defn firefox? []
  (.isFirefox browser))

(defn ie? []
  (.isIE browser))

(defn webview? []
  (.isIosWebview browser))

(defn safari? []
  (.isSafari browser))

(defn silk? []
  (.isSilk browser))

(defn opera? []
  (.isOpera browser))

(defn get-browser
  "Returns a keyword with the Browser, i.e :chrome, :safari, :firefox"
  []
  (cond
    (android-browser?) :android-browser
    (chrome?) :chrome
    (coast?) :coast
    (firefox?) :firefox
    (ie?) :ie
    (webview?) :webview
    (safari?) :safari
    (silk?) :silk
    (opera?) :opera
    :else :unknown))

(println (chrome?))
(println (android-browser?))
(println (opera?))
(println (get-browser))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
) 

