// 리스트 B.4 스레드 안전한 통지(하지만 실제로는 위험함)

protected synchronized void notifyOnline(Session s)
{
    for (l : listeners) l.online(s);
}
