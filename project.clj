(defproject maxwell "0.1.0-SNAPSHOT"
  :description "Minimal wrapper around goog.library to log client info & errors"
  :url "https://github.com/bensu/maxwell"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :scm {:name "git"
        :url "https://github.com/bensu/maxwell"}

  :deploy-repositories [["clojars" {:creds :gpg}]]

  ;; TODO: specify the closure library directly
  :dependencies [[org.clojure/clojure "1.7.0-beta2" :scope "provided"]
                 [org.clojure/clojurescript "0.0-3269" :scope "provided"]] 

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  
  :profiles {:dev {:plugins [[lein-cljsbuild "1.0.5"]
                             [codox "0.8.12"]
                             [lein-figwheel "0.3.1"]]}
             :doc {:dependencies [[org.clojure/clojurescript "0.0-2985"]]}}
  
  :codox {:language :clojurescript
          :src-dir-uri "https://github.com/bensu/maxwell/tree/master/"
          :src-linenum-anchor-prefix "L"}

  :cljsbuild 
  {:builds [{:id "dev"
             :source-paths ["src"]
             :figwheel :true 
             :compiler {:main maxwell.spy
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
                        :main maxwell.spy
                        :optimizations :advanced
                        :pretty-print false}}]})
