package guide.gui;

public class Character {
    private String name;
    private int[] stats;

    public Character(String[] data) {
        if (data != null && data.length > 0) {
            int[] stats = new int[data.length - 1];
            try {
                for (int i = 1; i < data.length; ++i) {
                	stats[i - 1] = Integer.parseInt(data[i]);
                    System.out.println(stats[i-1]);
                }
            } catch (NumberFormatException e) {
            }
            this.name = data[0];
            this.stats = stats;
        }
    }

    public Character(String name, int[] stats) {
        this.name = name;
        this.stats = stats;
    }

    @Override
    public String toString() {
        String result = name + ",";
        String sep = "";
        for (int elem : stats) {
            result += (sep + elem);
            sep = ",";
        }
        return result;

    }
}