(ns seqpeek.count-reads-test
  (:use clojure.test
        seqpeek.count-reads))

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

(deftest test-build-filter
  (testing "build-filter-null"
    (let [seq-filter (build-filter {})]
      (is (true? (seq-filter "")))
      (is (true? (seq-filter "A")))
      (is (true? (seq-filter "AAAAAAGGGGGGGGTTTT")))))
  (testing "build-filter-min"
    (let [seq-filter (build-filter {:min-length 8})]
      (is (false? (seq-filter "")))
      (is (false? (seq-filter "A")))
      (is (false? (seq-filter "CTGTACC")))
      (is (true? (seq-filter "CTCGAGGG")))
      (is (true? (seq-filter "CCGGCTCGAGTTTTTTTG")))))
  (testing "build-filter-max"
    (let [seq-filter (build-filter {:max-length 6})]
      (is (true? (seq-filter "")))
      (is (true? (seq-filter "CCGG")))
      (is (true? (seq-filter "CTCGAG")))
      (is (false? (seq-filter "GCTCGAGC")))))
  (testing "build-filter-min-max"
    (let [seq-filter (build-filter {:min-length 1 :max-length 4})]
      (is (false? (seq-filter "")))
      (is (true? (seq-filter "G")))
      (is (true? (seq-filter "CCGG")))
      (is (false? (seq-filter "CTCGA"))))))
