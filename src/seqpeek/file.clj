(ns seqpeek.file
  (import (java.io BufferedReader File FileReader)))

(defn make-line-seq
  "Reads the filename and returns a sequence over the lines in the file."
  [filename]
  (line-seq (BufferedReader. (FileReader. (File. filename)))))
