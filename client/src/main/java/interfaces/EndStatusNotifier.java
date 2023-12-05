package interfaces;

public interface EndStatusNotifier<T> {
    void setOptionListener(EndStatusListener<T> listener);
}
