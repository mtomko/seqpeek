(ns seqpeek.core
  (:require [clojure.cor.reducuers :as r])
  (:import [org.biojava3.sequencing.io.fastq FastqReader IlluminaFastqReader]
           [java.io File])
  (:gen-class)) 

(defmulti count-reads
  "Returns the number of reads in a sequencing data file"
  class)

(defmethod count-reads File [file]
  (let [reader (IlluminaFastqReader.)]
    (count (seq (.read reader file)))))

(defmethod count-reads String [filename]
  (count-reads (File. filename)))

(defn count-long-reads 
  [file n]
  (reduce + (r/filter #((> n .length (.getSequence %))) (seq (.read reader file)))))

(defn -main
  "The main entry point for seqpeek"
  [command & args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))

  ;; delegate base on command passed by the user
  (case command
    "count-reads" (println (count-reads (first args)))
    (println (str "Unknown command: " (first args)))))

