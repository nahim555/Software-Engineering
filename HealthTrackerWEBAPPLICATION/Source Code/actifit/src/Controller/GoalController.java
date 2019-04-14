package Controller;

import Model.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class GoalController {

    /**
     * Method to get all of the weight loss goals of a given user.
     *
     * @param user the user to find the goals of
     * @return
     */
    public static List<WeightLossGoal> getWeightLossGoals(User user) {
        List<WeightLossGoal> goals = new ArrayList<>();

        for (Goal goal : user.getGoals()) {
            if ((goal instanceof WeightLossGoal)) {
                goals.add((WeightLossGoal) goal);
            }
        }

        return goals;
    }

    /**
     * Method to get all of the distance goals of a given user.
     *
     * @param user the user to find the goals of
     * @return
     */
    public static List<DistanceGoal> getDistanceGoals(User user) {
        List<DistanceGoal> goals = new ArrayList<>();

        for (Goal goal : user.getGoals()) {
            if ((goal instanceof DistanceGoal)) {
                goals.add((DistanceGoal) goal);
            }
        }

        return goals;
    }

    /**
     * Method to get all of the time goals of a given user.
     *
     * @param user the user to find the goals of
     * @return
     */
    public static List<TimeGoal> getTimeGoals(User user) {
        List<TimeGoal> goals = new ArrayList<>();

        for (Goal goal : user.getGoals()) {
            if ((goal instanceof TimeGoal)) {
                goals.add((TimeGoal) goal);
            }
        }

        return goals;
    }

    /**
     * Method to get the number of days that are left to complete a goal.
     *
     * @param goal the goal to find the days left for
     * @return the number of days remaining to complete the goal
     */
    public static int getDaysRemaining(Goal goal) {
        int daysSinceStart = Period.between(goal.getStartingDate(), LocalDate.now()).getDays();
        int daysRemaining = goal.getDuration() - daysSinceStart;
        if (daysRemaining < 0) {
            daysRemaining = 0;
        }
        return daysRemaining;
    }

    /**
     * Method to calculate the percentage completion of a weight loss goal.
     *
     * @param user the user who owns the goal
     * @param goal the weight loss goal
     * @return the percentage completion of the given weight loss goal
     */
    public static double calculatePercentageComplete(User user, WeightLossGoal goal) {
        double percentage = (goal.getStartingWeight() - user.getMostRecentWeight()) /
                (goal.getStartingWeight() - goal.getTargetWeight()) * 100;
        if (percentage > 100) {
            percentage = 100;
        } else if (percentage < 0) {
            percentage = 0;
        }
        return percentage;
    }

    /**
     * Method to calculate the percentage completion of a distance goal.
     *
     * @param user the user who owns the goal
     * @param goal the distance goal
     * @return the percentage completion of the given distance goal
     */
    public static double calculatePercentageComplete(User user, DistanceGoal goal) {
        double percentage = ((double)ActivityController.getDistancePersonalBest(user, goal.getExercise()) - (double)goal.getPreviousBestDistance()) /
                ((double)goal.getTargetDistance() - (double)goal.getPreviousBestDistance()) * 100;
        if (percentage > 100) {
            percentage = 100;
        } else if (percentage < 0) {
            percentage = 0;
        }
        return percentage;
    }

    /**
     * Method to calculate the percentage completion of a time goal.
     *
     * @param user the user who owns the goal
     * @param goal the time goal
     * @return the percentage completion of the given time goal
     */
    public static double calculatePercentageComplete(User user, TimeGoal goal) {
        double percentage = ((double)goal.getPreviousBestTime() -
                (double)ActivityController.calculateFastestForDistance(user, goal.getExercise(), goal.getDistance())) /
                ((double)goal.getPreviousBestTime() - (double)goal.getTargetTime()) * 100;
        if (percentage > 100) {
            percentage = 100;
        } else if (percentage < 0) {
            percentage = 0;
        }
        return percentage;
    }

    /**
     * Method to calculate the daily calorie deficit needed to achieve a weight loss goal.
     *
     * @param user the user who owns the goal
     * @param goal the weight loss goal
     * @return the daily calorie deficit needed to achieve the weight loss goal
     */
    public static int calculateDailyCalorieDeficit(User user, WeightLossGoal goal) {
        double totalToLose = user.getMostRecentWeight() - ((WeightLossGoal) goal).getTargetWeight();
        int daysLeft = Period.between(LocalDate.now(), goal.getStartingDate().plusDays(goal.getDuration())).getDays();
        if (daysLeft < 1) {
            return 0;
        }
        return (int) (totalToLose / daysLeft * 7800);
    }

    /**
     * Method to see if a weight loss goal is too much for a user.
     *
     * @param user the user who owns the goal
     * @param goal the goal to check
     * @return true if the goal is too much for the user to reasonably achieve
     */
    public static boolean isGoalTooMuch(User user, WeightLossGoal goal) {
        if ((double) calculateDailyCalorieDeficit(user, goal) > 0.25 * (double) UserController.findBMR(user)) {
            return true;
        } else {
            return false;
        }
    }
}
