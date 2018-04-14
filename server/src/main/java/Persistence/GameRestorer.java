package Persistence;

public class GameRestorer implements IGameRestorer {
    private static GameRestorer ourInstance = new GameRestorer();

    public static GameRestorer getInstance() {
        return ourInstance;
    }

    private GameRestorer() {
    }


}
