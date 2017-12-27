// 리스트 B.6 리스너를 스레드 안전하게 제거하기(정말 그런가?)

public synchronized void removeListener(Listener l) {
    listeners.remove(i);
}
