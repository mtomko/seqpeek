(ns seqpeek.file
  (import (java.io BufferedReader File FileReader)))

(defmulti file-reader class)

(defmethod file-reader File
  [file]
  (BufferedReader. (FileReader. file)))

(defmethod file-reader String
  [filename]
  (file-reader (File. filename)))

(defn parent-of
  [file]
  (.getParentFile file))
