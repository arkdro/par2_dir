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

(defn build_full_output_dir
  [outdir shortened_input_dir]
  (java.nio.file.Paths/get outdir (into-array [shortened_input_dir])))

(defn build_new_output_dir
  [outdir to_delete file]
  (let [input_file_dir (get_input_file_directory file)
        shortened_input_dir (delete_part input_file_dir to_delete)]
    (build_full_output_dir outdir shortened_input_dir)))

(defn get_input_files
  [indir]
  (filter #(.isFile %)
          (file-seq (clojure.java.io/file indir))))

(defn create_output_dir
  [output_dir filename]
  (let [output_dir_with_file (java.nio.file.Paths/get (.toString output_dir) (into-array [filename]))]
    (clojure.java.io/make-parents (.toFile output_dir_with_file))))

(defn get_filename
  [file]
  (.getName file))

(defn build_link_name
  [dir filename]
  (java.nio.file.Paths/get (.toString dir) (into-array [filename])))

(defn file_exists?
  [file]
  (.exists (clojure.java.io/as-file (.toString file))))

(defn create_link
  [output_dir file]
  (let [filename (get_filename file)
        link (build_link_name output_dir filename)
        stub_attributes (make-array java.nio.file.attribute.FileAttribute 0)]
    (create_output_dir output_dir filename)
    (if (file_exists? link)
      link
      (java.nio.file.Files/createSymbolicLink link (.toPath file) stub_attributes))))

(defn call_par2
  [link]
  (let [link_string (.toString link)
        ;result (clojure.java.shell/sh "par2" "create" "-r100" link_string)
        result (clojure.java.shell/sh "ls" "-l" link_string)
        ]
    (println "exit code:" (:exit result))
    (println "output:\n" (:out result))))

(defn remove_link
  [link]
  ;;(java.nio.file.Files/deleteIfExists link)
  )

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
  (dorun
   (for [file files]
     (process_one_file outdir to_delete file))))

(defn process_input_dir
  [indir outdir to_delete]
  (let [files (get_input_files indir)]
    (process_files outdir to_delete files)))
