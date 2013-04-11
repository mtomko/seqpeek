(ns seqpeek.plot
  (:use [clojure.tools.cli :only[cli]]
        [incanter core io stats charts]
        [seqpeek.command]
        [seqpeek.fastq :only [fastq-file-seq]]))

(defn- parse-args
  "Argument parser for the count-reads command."
  [args]
  (cli args
       ["-l" "--read-length" "Plot read length histogram" :flag true]
       ["-o" "--output-file" "Output file" :flag false]
       ["-h" "--help" "Display usage and quit" :flag true]))

(defn read-lengths
  [filename]
  (map (comp count :seq)
       (fastq-file-seq filename)))

(defn- plot-body
  "The body of the plot command."
  [options files]
  (doseq [filename files]
    (let [hist (histogram
           (read-lengths filename)
           :x-label "read length (nt)"
           :y-label "frequency"
           :title   (str "Read Lengths for " filename))]
      (if-let [output-file (:output-file options)]
        (save hist output-file)
        (view hist)))))

(defn plot
  "The entry point for the plot command."
  [args]
  (command parse-args args plot-body))
