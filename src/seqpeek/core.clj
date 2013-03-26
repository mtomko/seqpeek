(ns seqpeek.core
  ;; (:require [clojure.core.reducuers :as r])
  (:import java.io.File
           (org.biojava3.sequencing.io.fastq FastqReader IlluminaFastqReader SangerFastqReader))
  (:gen-class)) 

(defn fastq-seq
  "Returns a sequence view of the FASTQ reader."
  [reader file]
  (seq (.read reader file )))

(defn sequences
  "Returns a lazy sequence of just the raw sequence data in the FASTQ"
  [reader file]
  (map #(.getSequence %) (fastq-seq reader file)))

(defn count-long-reads 
  [reader file n]
  (count (filter
          #(<= n (count %))
         (sequences reader file))))

(defn -main
  "The main entry point for seqpeek"
  [command & args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))

  ;; delegate base on command passed by the user
  (case command
    "count-reads" (println
                   (count (fastq-seq (IlluminaFastqReader.)
                                     (File. (first args)))))
    "count-long-reads" (println
                        (count-long-reads (IlluminaFastqReader.)
                                          (File. (first args))
                                          (Integer/parseInt (second args))))
    (println (str "Unknown command: " (first args)))))

