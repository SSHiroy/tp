package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.interview.InterviewRecord;

/**
 * Jackson-friendly version of {@link InterviewRecord}.
 */
class JsonAdaptedInterviewRecord {

    private final String id;
    private final String date;
    private final String notes;

    @JsonCreator
    public JsonAdaptedInterviewRecord(@JsonProperty("id") String id,
                                      @JsonProperty("date") String date,
                                      @JsonProperty("notes") String notes) {
        this.id = id;
        this.date = date;
        this.notes = notes;
    }

    public JsonAdaptedInterviewRecord(InterviewRecord source) {
        id = source.getId();
        date = source.getDate();
        notes = source.getNotes();
    }

    public InterviewRecord toModelType() throws IllegalValueException {
        return new InterviewRecord(id, date, notes);
    }
}

