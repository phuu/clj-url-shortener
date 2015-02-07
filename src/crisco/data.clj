(ns crisco.data)

(def ^:private urls (atom {:gh {:target "https://github.com" :redirects 2}
                           :gg {:target "https://google.com" :redirects 1}}))

(defn- get-target [slug]
  (get-in @urls [(keyword slug) :target]))

(defn store-slug! [slug target]
  (if-not (get-target slug)
    (swap! urls #(assoc %1 (keyword slug) {:target target :redirects 0}))))

(defn request-redirect! [slug]
  (when-let [target (get-target slug)]
    (swap! urls #(update-in %1 [(keyword slug) :redirects] (fnil inc 0)))
    target))
