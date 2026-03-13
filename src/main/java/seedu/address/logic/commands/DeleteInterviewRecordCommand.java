package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes an interview record from an existing person in the address book.
 */
public class DeleteInterviewRecordCommand extends Command {

    public static final String COMMAND_WORD = "deleteInterviewRecord";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an interview record from the person identified by the index number "
            + "used in the displayed person list.\n"
            + "Parameters: PERSON_INDEX RECORD_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_SUCCESS = "Interview record deleted from: %1$s";
    public static final String MESSAGE_INVALID_RECORD_INDEX = "The interview record index provided is invalid.";

    private final Index personIndex;
    private final Index recordIndex;

    /**
     * Creates a DeleteInterviewRecordCommand to delete the specified interview record
     * from the specified person.
     */
    public DeleteInterviewRecordCommand(Index personIndex, Index recordIndex) {
        requireNonNull(personIndex);
        requireNonNull(recordIndex);
        this.personIndex = personIndex;
        this.recordIndex = recordIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        if (recordIndex.getZeroBased() >= personToEdit.getInterviewRecords().size()) {
            throw new CommandException(MESSAGE_INVALID_RECORD_INDEX);
        }

        Person editedPerson = personToEdit.removeInterviewRecord(recordIndex.getZeroBased());
        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteInterviewRecordCommand)) {
            return false;
        }

        DeleteInterviewRecordCommand otherCommand = (DeleteInterviewRecordCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && recordIndex.equals(otherCommand.recordIndex);
    }
}