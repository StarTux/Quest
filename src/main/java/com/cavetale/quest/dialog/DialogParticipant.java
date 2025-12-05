package com.cavetale.quest.dialog;

import java.util.UUID;
import lombok.Data;

@Data
public final class DialogParticipant {
    private final UUID uuid;
    private boolean readOnly;
}
