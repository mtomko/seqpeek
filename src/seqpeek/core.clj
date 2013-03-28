(ns seqpeek.core
  (:use seqpeek.count-reads
        seqpeek.fastq
        seqpeek.file
        seqpeek.seq)
  (:gen-class)) 

(defn sequences
  [filename]
  (-> filename make-line-seq fastq-sequences))

(defn -main
  "The main entry point for seqpeek"
  [command dialect & args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (case (clojure.string/lower-case command)
    "count" (count-reads dialect args)
    "count-reads" (println 
                   (count (sequences (first args))))

    "count-long-reads" (println
                        (count-long-reads (sequences (first args))
                                          (Integer/parseInt (second args))))
    (println (str "Unknown command: " (first args)))))

