(ns seqpeek.fastq-test
  (:use clojure.test
        seqpeek.fastq))

(def fastq-record1
  ["@JE07D:00028:00073"
   "TAAGGAGAACGATCGATTTCTTGGCTTTATATATCTTGTGGATACGCTGCTTTA"
   "+"
   ";>>BABCD@CCCB;?49919@9?@ABA>BB;<=BBB>B;;8<AA@>@@999@19"])

(def fastq-record2
  ["@JE07D:00031:00075"
   "TAAGGAGAACGATCGATTTCTTGGCTTTATATATCTTGTGGAAAGGACGAACCGCCGAGTCTGC"
   "+"
   "@@6;8;BC?AB@;<999A<BA@B?<;;19C@<<=CCBCCC@BA>B@BBB;;;6.0,.....14/"])

(def fastq-record3
  ["@JE07D:00033:00077"
   "TAAGGAGAACGATCGATTTCTTGGCTTTATATATCTTGTGGAAAGGACGAGGTCCCACATCCTCACCCC"
   "+"
   ">@49544959BBBCCCCCBCC@C@BCD@DDBBBCCCACCB=@><@<@@>9958991/,,,,*,,,0000"])

(def fastq-sequence
  (seq (list fastq-record1 fastq-record2 fastq-record3)))

(deftest test-fastq-sequences
  (testing "seqpeek.fastq/fastq-sequences"
    (is (= (seq (list "TAAGGAGAACGATCGATTTCTTGGCTTTATATATCTTGTGGATACGCTGCTTTA"))
           (fastq-sequences fastq-record1))))
  (testing "seqpeek.fastq/fastq-seq")
    (is (= (seq (list {:id "@JE07D:00028:00073"
                       :seq "TAAGGAGAACGATCGATTTCTTGGCTTTATATATCTTGTGGATACGCTGCTTTA"
                       :qual ";>>BABCD@CCCB;?49919@9?@ABA>BB;<=BBB>B;;8<AA@>@@999@19"}))
           (fastq-seq fastq-record1))))
