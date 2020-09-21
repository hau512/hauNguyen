package com.track.tracker.response;

import java.util.List;
import java.util.Set;

import com.track.tracker.model.TrackProfile;

public class TrackerProfileResponse extends BaseResponse<TrackProfile, ErrorResponse> {

	public TrackerProfileResponse() {
		super();
	}

	public TrackerProfileResponse(String message, String code, List<TrackProfile> result, Set<ErrorResponse> error) {
		super(message, code, result, error);
	}
}
