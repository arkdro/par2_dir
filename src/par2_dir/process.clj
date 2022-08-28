(ns par2-dir.process)
(ns par2-dir.process
  (:require [clojure.string :as str]))

(def root "/")

(defn get_input_file_directory
  [file]
  ;; FIXME handle the root directory: '/'
  (.getParent file))

(defn sanitize_to_delete
  [dir]
  (let [without_end (str/replace dir #"/+$" "")]
    (if (str/blank? without_end)
      root
      without_end)))

(defn delete_part
  [dir to_delete]
  ;; FIXME handle unicode
  (let [sanitized (sanitize_to_delete to_delete)
        sanitized_length (count sanitized)]
    (cond
      (= sanitized root) dir
      (str/starts-with? dir sanitized) (subs dir sanitized_length)
      :else dir)))


(defn process_input_dir
  [indir outdir to_delete]
  (println indir)
  (println outdir)
  (println to_delete)
  )
