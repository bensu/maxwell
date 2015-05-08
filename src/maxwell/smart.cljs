(ns maxwell.smart)

;; Browser
;; =======

(def browser-obj goog.labs.userAgent.browser)

;; Note: All this fns can be safely memoized

(defn android-browser? []
  (.isAndroidBrowser browser-obj))

(defn chrome? []
  (.isChrome browser-obj))

(defn coast? []
  (.isCoast browser-obj))

(defn firefox? []
  (.isFirefox browser-obj))

(defn ie? []
  (.isIE browser-obj))

(defn webview? []
  (.isIosWebview browser-obj))

(defn safari? []
  (.isSafari browser-obj))

(defn silk? []
  (.isSilk browser-obj))

(defn opera? []
  (.isOpera browser-obj))

;; TODO: The order should be optimized by browser popularity
(defn browser
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

(defn browser-version
  "Returns a string with the version in the vendor's format
   Ex: \"42.0.2311.135\" for Chrome"
  []
  (.getVersion browser-obj))

(defn browser>=
  "Whether the running browser version is higher or the same (>=) as the 
   given version.
  (running-browser-version >= given-version)
  Ex: if the running version is 3 then (browser>= 2) -> (3 >= 2) -> true 
      if the running version is \"42.0.2311.135\" then
        (browser>= \"43\") -> (\"42.0.2311.135\" >= \"43\") -> false"
  [version]
  (.isVersionOrHigher browser-obj version))

;; Engine
;; ======

(def engine-obj (.-engine goog.labs.userAgent))

;; TODO: check Closure library version before using this 
(defn edge?
  "Included in the latest Closure Library"
  []
  (.isEdge engine-obj))

(defn gecko? []
  (.isGecko engine-obj))

(defn presto? []
  (.isPresto engine-obj))

(defn trident? []
  (.isTrident engine-obj))

(defn webkit? []
  (.isWebKit engine-obj))

;; TODO: The order should be optimized by engine popularity
(defn engine
  "Returns a keyword with the Browser
  Ex: :chrome, :safari, :firefox"
  []
  (cond
    (gecko?) :gecko
    (presto?) :presto
    (trident?) :trident
    (webkit?) :webkit
    (edge?) :edge
    :else :unknown))

;; Engine Version
;; ==============

(defn engine-version
  "Gets the running engine version or \"\" if it can't be determined"
  []
  (.getVersion engine-obj))

(defn engine>=
  "Whether the running engine version is higher or the same (>=) as the 
   given version.
  (running-engine-version >= given-version)
  Ex: if the running version is 3 then (engine>= 2) -> (3 >= 2) -> true 
      if the running version is \"537.36\" then
        (browser>= \"540\") -> (\"537.36\" >= \"540\") -> false"
  [version]
  (.isVersionOrHigher engine version))

;; Platform

(def platform-obj goog.labs.userAgent.platform)

(defn android? []
  (.isAndroid platform-obj))

(defn chrome-os? []
  (.isChromeOS platform-obj))

(defn ios? []
  (.isIos platform-obj))

(defn ipad? []
  (.isIpad platform-obj))

(defn iphone? []
  (.isIphone platform-obj))

(defn ipod? []
  (.isIpod platform-obj))

(defn linux? []
  (.isLinux platform-obj))

(defn mac? []
  (.isMachintosh platform-obj))

(defn windows? []
  (.isWindows platform-obj))

(defn platform []
  (cond
    (android?) :android
    (chrome-os?) :chrome-os
    (ios?) :ios
    (ipad?) :ipad
    (iphone?) :iphone
    (ipod?) :ipod
    (linux?) :linux
    (mac?) :mac
    (windows?) :windows))

(defn platform-version []
  (.getVersion platform-obj))

;; TODO: correct versions for Docstring
(defn platform>=
  "Whether the running platform version is higher or the same (>=) as the 
   given version.
  (running-platform-version >= given-version)
  Ex: if the running version is 3 then (engine>= 2) -> (3 >= 2) -> true 
      if the running version is \"537.36\" then
        (browser>= \"540\") -> (\"537.36\" >= \"540\") -> false"
  [version]
  (.isVersionOrHigher platform-obj version))

;; TODO: spy-platform

;; User Agent 
;; ==========

(def util-obj (.-util goog.labs.userAgent))

(defn agent []
  (.getUserAgent util-obj))

(defn agent->tuples [agent]
  (js->clj (.extractVersionTuples util-obj agent)))

;; Screen
;; ======

;; Source: http://stackoverflow.com/questions/3437786/get-the-size-of-the-screen-current-web-page-and-browser-window   
(defn screen
  "Returns the size of the inner screen in px in a vector [x y]
  Ex: [1600 720]"
  []
  (let [w js/window
        d js/document
        e (.-documentElement d)
        g (aget (.getElementsByTagName d "body") 0)]
    [(or (.-innerWidth w) (.-clientWidth e) (.-clientWidth g))
     (or (.-innerHeight w) (.-clientHeight e) (.-clientHeight g))]))

;; User
;; ====

;; TODO: conditionally add platform & device
(defn spy
  "Returns a map with all available user info"
  []
  {:browser (browser)
   :browser-version (browser-version)
   :platform (platform)
   :platform-version (platform-version)
   :engine (engine)
   :engine-version (engine-version)
   :screen (screen)
   :agent (agent)})
