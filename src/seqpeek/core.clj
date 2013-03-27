(ns seqpeek.core
  (:use [seqpeek.count-reads]
        [seqpeek.seq])
  (:import java.io.File)
  (:gen-class)) 

(declare main)
(defn -main
  "The main entry point for seqpeek"
  [command dialect & args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (case (clojure.string/lower-case command)
    "count" (count-reads dialect args)
    "count-reads" (println 
                   (count (fastq-seq (fastq-reader dialect)
                                     (File. (first args)))))
    "count-long-reads" (println
                        (count-long-reads (fastq-reader dialect)
                                          (File. (first args))
                                          (Integer/parseInt (second args))))
    (println (str "Unknown command: " (first args)))))

