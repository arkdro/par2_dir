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

;; (defn delete_part2
;;   [file to_delete]
;;   (let [
;;         starts_with_deleted (.startsWith (.toPath file) to_delete)
;;         to_delete_path (java.nio.file.Paths/get (java.net.URI. (str "file://" to_delete)))
;;         to_delete_number_of_parts (.getNameCount to_delete_path)
;;         to_delete_index (dec to_delete_number_of_parts)
;;         file_path_number_of_parts (.getNameCount (.toPath file))
;;         last_part_index (dec file_path_number_of_parts)
;;         shortened_file_path (.subpath (.toPath file) to_delete_index last_part_index)
;;         ]
;;     shortened_file_path
;;     )
;;   )

(defn create_full_output_dir
  [outdir shortened_input_dir]
  (java.nio.file.Paths/get outdir (into-array [shortened_input_dir])))

(defn build_new_output_dir
  [outdir to_delete file]
  (let [input_file_dir (get_input_file_directory file)
        shortened_input_dir (delete_part input_file_dir to_delete)]
    (create_full_output_dir outdir shortened_input_dir)))

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
