(ns seqpeek.core
  (:require [seqpeek.count])
  (:use [seqpeek.seq])
  (:import java.io.File
           (org.biojava3.sequencing.io.fastq FastqReader
                                             IlluminaFastqReader
                                             SangerFastqReader
                                             SolexaFastqReader))
  (:gen-class)) 

(declare main)
(defn -main
  "The main entry point for seqpeek"
  [command dialect & args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))

  ;; delegate base on command passed by the user
  (case command
    "count" (seekpeek.count/count-cmd dialect args)
    "count-reads" (println 
                   (count (fastq-seq (get-reader dialect)
                                     (File. (first args)))))
    "count-long-reads" (println
                        (count-long-reads (IlluminaFastqReader.)
                                          (File. (first args))
                                          (Integer/parseInt (second args))))
    (println (str "Unknown command: " (first args)))))

