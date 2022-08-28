(ns par2-dir.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            ;; [clojure.tools.trace :as trace]
            [par2-dir.process]))

(def cli-options
  ;; An option with a required argument
  [["-i" "--indir INDIR" "input dir"
    :validate [#(and
                 (some? %)
                 (string? %))
               "Must be non-empty string"]]
   ["-o" "--outdir OUTDIR" "output dir"
    :validate [#(and
                 (some? %)
                 (string? %))
               "Must be non-empty string"]]
   ["-d" "--delete PART" "part to delete from the input dir"
    :validate [#(and
                 (some? %)
                 (string? %))
               "Must be non-empty string"]]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])


(defn activate_trace
  []
  ;; (trace/trace-ns 'add-lang.process)
  ;; (trace/trace-ns 'add-lang.process-files)
  )

(defn -main
  "Parse command line arguments and run the generator"
  [& args]
  (activate_trace)
  (let [opts (parse-opts args cli-options)
        options (get opts :options)
        indir (get options :indir)
        outdir (get options :outdir)
        to_delete (get options :delete)
        errors (get opts :errors)
        help (get-in opts [:options :help])]
    (cond
      help (println (get opts :summary))
      errors (println errors)
      :default (par2-dir.process/process_input_dir indir outdir to_delete))))
