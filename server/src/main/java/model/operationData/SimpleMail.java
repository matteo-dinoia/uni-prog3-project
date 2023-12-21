package model.operationData;

import java.util.Date;

public record SimpleMail(String source, String destinations, String object, String content, int id, Date date) {
}
