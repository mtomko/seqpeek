(defproject seqpeek "0.1.0-SNAPSHOT"
  :description "A simple utility for examining next-gen sequencing data"
  :url "http://github.com/mtomko/seqpeek"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[incanter "1.4.1"]
                 [org.clojure/clojure "1.5.1"]
                 [org.biojava/biojava3-core "3.0.5"]
                 [org.biojava/biojava3-sequencing "3.0.5"]]
  :repositories [["biojava" "http://biojava.org/download/maven/"]]
  :main seqpeek.core)
