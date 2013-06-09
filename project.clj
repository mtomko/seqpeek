(defproject seqpeek "0.1.0-SNAPSHOT"
  :description "A simple utility for examining next-gen sequencing data"
  :url "http://github.com/mtomko/seqpeek"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories [["biojava" "http://biojava.org/download/maven/"]]
  :dependencies [[incanter "1.5.0"]
                 [org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.cli "0.2.2"]]
                 ;[org.biojava/biojava3-core "3.0.5"]
                 ;[org.biojava/biojava3-sequencing "3.0.5"]]
  :profiles {:dev {:dependencies [[speclj "2.5.0"]]}}
  :plugins [[speclj "2.5.0"]]
  :test-paths ["spec/"]
  :main seqpeek.core)
