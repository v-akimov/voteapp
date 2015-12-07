package ru.akimov.voteapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by z003cptz on 06.12.2015.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResult {
    private long score;
    private String restorantName;
}
