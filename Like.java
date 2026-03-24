import java.util.HashSet;
import java.util.Set;

/**
 * Tracks which users have “liked” a given Post.
 *
 * toggleLike(userId):
 *   – if userId was already in the set, remove it (un‑like)
 *   – otherwise add it (like)
 *
 * isLikedBy(userId): true if userId is in the set
 * count(): total number of likes
 */
public class Like {
    private final Set<String> userIds = new HashSet<>();

    /**
     * Toggle this user’s like:
     *  – if present, un-like (remove)
     *  – otherwise, like (add)
     */
    public void toggleLike(String userId) {
        if (userIds.contains(userId)) {
            userIds.remove(userId);
        } else {
            userIds.add(userId);
        }
    }

    /**
     * @return true if this user has liked already
     */
    public boolean isLikedBy(String userId) {
        return userIds.contains(userId);
    }

    /**
     * @return total number of current likes
     */
    public int count() {
        return userIds.size();
    }
}
