(ns seqpeek.core
  (:import (org.biojava3.sequencing.io.fastq FastqReader IlluminaFastqReader)
           (java.io File))
  (:gen-class))

(defmulti count-reads
  "Returns the number of reads in a sequencing data file"
  class)

(defmethod count-reads File [file]
  (let [reader (IlluminaFastqReader. file)]
    (count (.read reader))))

(defmethod count-reads String [filename]
  (count-reads (File. filename)))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (println "Hello, World!"))
