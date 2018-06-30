package ru.alpaaka.testprofi.data.source.network;

import ru.alpaaka.testprofi.data.source.IDataSource;

public interface IVkDataSource {

    void loadFriends(IDataSource.OnDataLoadedCallback callback, int offset);
}
