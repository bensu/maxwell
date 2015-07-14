(ns maxwell.spy
  "Finds out all possible information from the client,
   including user-agent, screen size, browser, platform,
   engine, and their respective versions"
  (:import [goog.labs.userAgent browser engine platform util]))

;; Browser
;; =======

;; Note: All this fns can be safely memoized

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

;; TODO: The order should be optimized by browser popularity
(defn get-browser
  "Returns a keyword with the Browser
  Possible return values:
    :android-browser
    :chrome
    :coast
    :firefox
    :ie
    :webview
    :safari
    :silk
    :opera
    :unknown"
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
  (.getVersion browser))

(defn browser>=
  "Whether the running browser version is higher or the same (>=) as the 
   given version.
  (running-browser-version >= given-version)
  Ex: if the running version is 3 then (browser>= 2) -> (3 >= 2) -> true 
      if the running version is \"42.0.2311.135\" then
        (browser>= \"43\") -> (\"42.0.2311.135\" >= \"43\") -> false"
  [version]
  (.isVersionOrHigher browser version))

;; Engine
;; ======

(defn edge? []
  (.isEdge engine))

(defn gecko? []
  (.isGecko engine))

(defn presto? []
  (.isPresto engine))

(defn trident? []
  (.isTrident engine))

(defn webkit? []
  (.isWebKit engine))

;; TODO: The order should be optimized by engine popularity
(defn get-engine
  "Returns a keyword with the Engine 
  Possible return values:
    :gecko
    :presto
    :webkit
    :trident
    :edge
    :unknown"
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
  (.getVersion engine))

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

(defn android? []
  (.isAndroid platform))

(defn chrome-os? []
  (.isChromeOS platform))

(defn ios? []
  (.isIos platform))

(defn ipad? []
  (.isIpad platform))

(defn iphone? []
  (.isIphone platform))

(defn ipod? []
  (.isIpod platform))

(defn linux? []
  (.isLinux platform))

(defn mac? []
  (.isMacintosh platform))

(defn windows? []
  (.isWindows platform))

(defn get-platform
  "Returns a keyword with the Platform 
   Possible return values: 
     :android
     :chrome-os
     :ios
     :ipad
     :iphone
     :ipod
     :linux
     :mac
     :windows
     :unknown"
  []
  (cond
    (android?) :android
    (chrome-os?) :chrome-os
    (ios?) :ios
    (ipad?) :ipad
    (iphone?) :iphone
    (ipod?) :ipod
    (linux?) :linux
    (mac?) :mac
    (windows?) :windows
    :else :unknown))

(defn platform-version
  "Returns the platform version. Returns \"\" for Linux."
  []
  (.getVersion platform))

;; TODO: correct versions for Docstring
(defn platform>=
  "Whether the running platform version is higher or the same (>=) as the 
   given version.
  (running-platform-version >= given-version)
  Ex: if the running version is 3 then (engine>= 2) -> (3 >= 2) -> true 
      if the running version is \"537.36\" then
        (browser>= \"540\") -> (\"537.36\" >= \"540\") -> false"
  [version]
  (.isVersionOrHigher platform version))

;; User Agent 
;; ==========

(defn agent
  "Returns the user agent string.
   Ex: \"Mozilla/5.0 (X11; Linux x86_64)..\" "
  []
  (.getUserAgent util))

(defn agent->tuples
  "Transforms the user-agent string into a set of tuples
  Ex: [[\"Mozilla/5.0\" \"(X11; Linux x86_64)\"] [\"AppleWebKit/537.36 ...\"]]"
  [agent]
  (js->clj (.extractVersionTuples util agent)))

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

(defn all-info 
  "Returns a map with all available user info:
     :browser
     :browser-version
     :platform 
     :platform-version 
     :engine
     :engine-version
     :screen
     :agent"
  []
  {:browser (get-browser)
   :browser-version (browser-version)
   :platform (get-platform)
   :platform-version (platform-version)
   :engine (get-engine)
   :engine-version (engine-version)
   :screen (screen)
   :agent (agent)})
