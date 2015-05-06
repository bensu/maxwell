(defproject maxwell "0.1.0-SNAPSHOT"
  :description "Minimal Wrapper around goog.userAgent to report stacktraces"
  :url "https://github.com/bensu/maxwell"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0-beta2" :scope "provided"]
                 [org.clojure/clojurescript "0.0-3211" :scope "provided"]] 

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  
  :profiles {:dev {:plugins [[lein-cljsbuild "1.0.5"]
                             [lein-figwheel "0.3.1"]]}}

  :cljsbuild 
  {:builds [{:id "dev"
             :source-paths ["src"]
             :figwheel :true 
             :compiler {:main maxwell.smart
                        :asset-path "js/compiled/out"
                        :output-to "resources/public/js/compiled/maxwell.js"
                        :output-dir "resources/public/js/compiled/out"
                        :optimizations :none
                        :source-map true
                        :source-map-timestamp true
                        :cache-analysis true }}
            {:id "min"
             :source-paths ["src"]
             :compiler {:output-to "resources/public/js/compiled/maxwell.js"
                        :main maxwell.smart
                        :optimizations :advanced
                        :pretty-print false}}]})
