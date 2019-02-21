(defproject ventas-slider-plugin "0.1.0"
  :description "Slider plugin for ventas"

  :url "https://github.com/joelsanchez/ventas-slider-plugin"

  :scm {:url "git@github.com:joelsanchez/ventas-slider-plugin.git"}

  :pedantic? :abort

  :author {:name "Joel Sánchez"
           :email "webmaster@kazer.es"}

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0" :exclusions [org.clojure/spec.alpha]]
                 [ventas-core "0.0.12-SNAPSHOT"]
                 ;; transitive
                 [org.apache.commons/commons-compress "1.9"]]

  :plugins [[lein-ancient "0.6.15"]
            [deraen/lein-sass4clj "0.3.1" :exclusions [org.apache.commons/commons-compress]]]

  :min-lein-version "2.6.1"

  :source-paths ["src/clj" "src/cljs"]

  :test-paths ["test/clj"]

  :jvm-opts ["-XX:-OmitStackTraceInFastThrow"
             ;; Disable empty/useless menu item in OSX
             "-Dapple.awt.UIElement=true"]

  :sass {:source-paths ["src/scss"]
         :target-path "resources/public/files/css"
         :source-map true
         :output-style :compressed}

  :repositories {"releases" {:url "https://clojars.org/repo"
                             :sign-releases false}
                 "snapshots" {:url "https://clojars.org/repo"
                              :sign-releases false}}

  :profiles {:dev {:repl-options {:init-ns repl
                                  :port 4001
                                  :nrepl-middleware [cider.piggieback/wrap-cljs-repl]
                                  :timeout 120000}
                   :dependencies [[thheller/shadow-cljs "2.7.21" :exclusions [org.clojure/tools.reader
                                                                              com.google.guava/guava
                                                                              org.clojure/tools.cli
                                                                              commons-codec
                                                                              commons-io
                                                                              ring/ring-core
                                                                              org.clojure/data.json]]
                                  [deraen/sass4clj "0.3.1" :exclusions [org.apache.commons/commons-compress]]
                                  [cider/piggieback "0.3.10" :exclusions [org.clojure/clojurescript org.clojure/tools.logging nrepl]]
                                  [binaryage/devtools "0.9.10"]
                                  [org.clojure/tools.namespace "0.3.0-alpha4" :exclusions [org.clojure/tools.reader]]]
                   :source-paths ["dev/clj" "dev/cljs"]}})