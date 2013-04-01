(ns seqpeek.plot
  (:use [clojure.tools.cli :only[cli]]
        [incanter core io stats charts]
        [seqpeek.command]
        [seqpeek.fastq :only [fastq-seq-over]]
        [seqpeek.file :only [make-line-seq]]))

(defn- parse-args
  "Argument parser for the count-reads command."
  [args]
  (cli args
       ["-l" "--read-length" "Plot read lengths" :flag false]
       ["-h" "--help" "Display usage and quit" :flag true]))

(defn read-length-hist
  [filename]
  (map (comp count :seq)
       (fastq-seq-over filename)))

;; this works interactively, but not in the embedded from - why?
;; check out
;; http://stackoverflow.com/questions/8892891/how-to-display-an-incanter-graph-in-jpanel/8895013#8895013
(defn- plot-body
  "The body of the plot command."
  [options files body]
  (doseq [filename files]
    (view (histogram
           (read-length-hist filename)))
    (read-line))
  (read-line))

(defn plot
  "The entry point for the plot command."
  [args]
  (command parse-args args plot-body))
