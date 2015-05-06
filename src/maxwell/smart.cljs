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
  "Returns a keyword with the Browser
  Ex: :chrome, :safari, :firefox"
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

;; Browser Version
;; ===============

(defn get-browser-version
  "Returns a string with the version in the vendor's format
   Ex: \"42.0.2311.135\" for Chrome"
  []
  (.getVersion browser))

(defn higher-version?
  "Takes a browser version, and <= compares to the current one
  Ex: (higher-version? 2) => true, because we are at version 3
      (higher-version \"42.0.2311.135\") => true because we are at 42.0.2311.135
      (higher-version 10) => false because we are at 8"
  [v]
  (.isVersionOrHigher browser v))

(println (chrome?))
(println (android-browser?))
(println (opera?))
(println (get-browser))
(println (get-browser-version))
(println (higher-version? "41"))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
) 

