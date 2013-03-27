(ns seqpeek.count
  (:require [seqpeek.seq])
  (:use [clojure.tools.cli :only[cli]]))

(def specs 
  (list
   ["-n" "--min-length" "Filter reads by minimum length" :parse-fn #(Integer. %)]
   ["-m" "--max-length" "Filter reads by maximum length" :parse-fn #(Integer. %)]
   ["-h" "--help" "Display usage and quit" :default false]))

(defn count-cmd
  "The entry point for the count function."
  [dialect args]
  (let [[options files banner] (cli args specs)]
    (when (:help options)
      (println banner)
      (System/exit 0))
    (println options)))
    
