// 리스트 B.5 훨씬 더 나은 스레드 안전한 통지

protected void notifyOnline(Session s) {
    List<Listener> ls;
    synchronized (this) {
	ls = listeners.clone();
    }
    for (l : ls) l.online(s);
}
