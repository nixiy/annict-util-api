package com.example.annictUtil.service;

import annict.UserQuery;
import annict.type.StatusState;
import com.example.annictUtil.model.UserStatus;
import com.example.annictUtil.repository.AnnictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnictService {
    @Autowired
    AnnictRepository annictRepository;

    /**
     * ※ statusStateには、誰に対して引いても自分のステータスが設定されている
     * ※ こちらを利用して、自分と検索ユーザの差分を出す
     * @param username
     * @param isMatch
     * @return
     */
    public List<UserStatus> getUser(String username, boolean isMatch) {
        List<UserStatus> userStatuses = new ArrayList<>();

        for (StatusState statusState: StatusState.values()) {
            //NO_STATE: ステータスをつけていない莫大な作品に対して検索をかけることになるため除外
            //$UNKNOWN: Enumで自動生成されたもので、Annict側にこのステータスは存在しないため除外
            if (!StatusState.NO_STATE.equals(statusState) && !StatusState.$UNKNOWN.equals(statusState)) {
                System.out.println("AnnictService.getUser call " + statusState.rawValue());
                long start = System.currentTimeMillis();
                UserQuery.User watchingUser = annictRepository.getUser(username, statusState);
                System.out.println("AnnictService.getUser called " + statusState.rawValue() + ", time[ms]: " + (System.currentTimeMillis() - start));
                convertToUserStatus(userStatuses, watchingUser, statusState, isMatch);
            }
        }

        return userStatuses;
    }

    /**
     *
     * @param user
     * @param searchTargetStatusState 検索対象のステータス
     * @param isMatch
     * @return
     */
    private void convertToUserStatus(List<UserStatus> userStatuses, UserQuery.User user, StatusState searchTargetStatusState, boolean isMatch) {
        user.works().nodes().forEach(v -> {

            // 自身と検索対象の視聴状態に差分があるか
            boolean isMatchYourself = v.viewerStatusState().rawValue().equals(searchTargetStatusState.rawValue());

            // isMatch:true  -> 検索対象と同一ステータスの作品のみリストに加える
            // isMatch:false -> 検索対象と異なるステータスの作品のみリストに加える
            if (isMatch == isMatchYourself) {
                UserStatus userStatus = new UserStatus();

                userStatus.setTitle(v.title());
                userStatus.setMyselfStatusState(v.viewerStatusState().rawValue());

                // 自分と検索対象のステータスが異なる場合のみ含める
                if(!isMatch) {
                    userStatus.setSearchTargetStatusState(searchTargetStatusState.rawValue());
                }

                userStatuses.add(userStatus);
            }
        });
    }
}
