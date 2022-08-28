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

(defn build_new_output_dir
  [outdir to_delete file]
  (let [input_file_dir (get_input_file_directory file)
        shortened_input_path (delete_part input_path to_delete)
        shortened_input_dir (get_dir shortened_input_path)
      ;  output_dir (create_full_output_dir)
        ]
    )
  )

(defn get_input_files
  [indir]
  (filter #(.isFile %)
          (file-seq (clojure.java.io/file indir))))

(defn process_one_file
  [outdir to_delete file]
  (let [new_output_dir (build_new_output_dir outdir to_delete file)
        link (create_link new_output_dir file)]
    (call_par2 link)
    (remove_link link)
    )
  )

(defn process_files
  [outdir to_delete files]
  (for [file files]
    (process_one_file outdir to_delete file)))

(defn process_input_dir
  [indir outdir to_delete]
  (let [files (get_input_files indir)]
    (process_files outdir to_delete files)))
