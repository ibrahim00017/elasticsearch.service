package com.rintio.elastic.elastic;

import java.util.List;

public class Hits {
   private int total;
   private int max_score;
   private List<HitObject> hits;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMax_score() {
        return max_score;
    }

    public void setMax_score(int max_score) {
        this.max_score = max_score;
    }

    public List<HitObject> getHits() {
        return hits;
    }

    public void setHits(List<HitObject> hits) {
        this.hits = hits;
    }
}
