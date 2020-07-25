package com.nisith.firebasenotification.Model;

import java.util.List;

public class MyResponse {
    public long multicast_id;
    public int success;
    public int failure;
    public int canonicals_ids;
    public List<Result> results;

    public MyResponse(){

    }

    public MyResponse(long multicast_id, int success, int failure, int canonicals_ids, List<Result> results) {
        this.multicast_id = multicast_id;
        this.success = success;
        this.failure = failure;
        this.canonicals_ids = canonicals_ids;
        this.results = results;
    }

    public void setMulticast_id(long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public void setCanonicals_ids(int canonicals_ids) {
        this.canonicals_ids = canonicals_ids;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public long getMulticast_id() {
        return multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public int getFailure() {
        return failure;
    }

    public int getCanonicals_ids() {
        return canonicals_ids;
    }

    public List<Result> getResults() {
        return results;
    }
}
