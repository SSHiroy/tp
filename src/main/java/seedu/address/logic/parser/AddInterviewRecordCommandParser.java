package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_NOTE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddInterviewRecordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interview.InterviewRecord;

/**
 * Parses input arguments and creates a new AddInterviewRecordCommand object.
 */
public class AddInterviewRecordCommandParser implements Parser<AddInterviewRecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddInterviewRecordCommand
     * and returns an AddInterviewRecordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddInterviewRecordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INTERVIEW_ID, PREFIX_INTERVIEW_DATE, PREFIX_INTERVIEW_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_INTERVIEW_ID, PREFIX_INTERVIEW_DATE, PREFIX_INTERVIEW_NOTE)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddInterviewRecordCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_INTERVIEW_ID, PREFIX_INTERVIEW_DATE, PREFIX_INTERVIEW_NOTE);

        Index personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());

        String id = argMultimap.getValue(PREFIX_INTERVIEW_ID).get();
        String date = argMultimap.getValue(PREFIX_INTERVIEW_DATE).get();
        String notes = argMultimap.getValue(PREFIX_INTERVIEW_NOTE).get();

        InterviewRecord interviewRecord = new InterviewRecord(id, date, notes);

        return new AddInterviewRecordCommand(personIndex, interviewRecord);
    }

    /**
     * Returns true if all prefixes contain non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

