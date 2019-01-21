package yichen.extraalchemy.blocks.alchemy_circle;

public class Counter {
    private float i = 0;
    private float old = 0;

    public float increment() {
        return increment(1);
    }
    public float increment(int amount) {
        old = i;
        i += amount;
        return i;
    }

    public float decrement() {
        return decrement(1);
    }

    public float decrement(int amount) {
        old = i;
        i -= amount;
        return i;
    }

    public void clear() {
        i = 0;
        old = 0;
    }

    public float oldValue() {
        return old;
    }

    public float value() {
        return i;
    }

    public void set(int amount) {
        old = i;
        this.i = amount;
    }
}