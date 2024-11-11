package store.model;

public class AmountInfo {
    private int amount;
    private int promotionDiscount;
    private int membershipDiscount;

    AmountInfo() {
        this.amount = 0;
        this.promotionDiscount = 0;
        this.membershipDiscount = 0;
    }

    public void addAmount(int amount) { this.amount += amount; }

    public void addPromotionDiscount(int promotionDiscount) {
        this.promotionDiscount += promotionDiscount;
    }

    public void addMembershipDiscount(int membershipDiscount) {
        this.membershipDiscount += membershipDiscount;
    }

    public int getAmount() { return amount; }

    public int getPromotionDiscount() { return promotionDiscount; }

    public int getMembershipDiscount() { return membershipDiscount; }

    public int getTotalAmount() {
        return amount - promotionDiscount - membershipDiscount;
    }
}
