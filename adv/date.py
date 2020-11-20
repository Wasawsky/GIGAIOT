##
##opcion 1
def _do_time(self, action):  # TIME received from ESP8266
        lnk = self._lnk
        try:
            t = int(action[0])
        except ValueError:  # Gibberish.
            lnk.quit('Invalid data from ESP8266')
            return
        self._time_valid = t > 0
        if self._time_valid:
            rtc = pyb.RTC()
            tm = localtime(t)
            hours = (tm[3] + self._local_time_offset) % 24
            tm = tm[0:3] + (tm[6] + 1,) + (hours,) + tm[4:6] + (0,)
            rtc.datetime(tm)
            self._rtc_last_syn = time()
            lnk.vbprint('time', localtime())
        else:
            lnk.vbprint('Bad time received.')

    # Is Pyboard RTC synchronised? 
	
##opcion 2
def perform(self) :
        localtime = self.get_localtime()
        secs = Scheduler.secs_since_midnight(localtime)
        (_year, _month, _mday, _hour, _minute, _second, wday, _yday) = localtime
        seq = self.get_current_seq(secs, wday + 1)
        if seq :
            if seq != self.current_seq :
                color_name = seq['color_name']
                logging.info("Scheduler: Setting lamp color to {}", color_name)
                self.lamp.set_colorspec(self.color_specs[color_name])
                self.current_seq = seq
        else :
            self.lamp.set_colorspec(self.color_specs['black'])
            self.current_seq = None
        return True
    
    

    ##
    ## Internal operations
    ## https://www.programcreek.com/python/example/102388/utime.localtime