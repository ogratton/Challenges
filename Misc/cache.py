from time import sleep
import timer

# Make a decorator to cache function results for unary functions

tts = 5  # time to sleep, seconds

def cached(method):
    cache = dict()
    def cache_lookup(*args, **kwargs):
        result = fast(cache, *args, **kwargs)
        if result:
            return result
        else:
            result = method(*args, **kwargs)
            add_to_cache(cache, result, *args, **kwargs)
            return result
    return cache_lookup

@timer.timeit
@cached
def slow(key):
    if key == 5:
        sleep(tts)
        return "wow"
    elif key == 4:
        sleep(tts)
        return "dog"
    elif key == 3:
        sleep(tts)
        return "pig"
    elif key == 2:
        sleep(tts)
        return "cat"
    elif key == 1:
        sleep(tts)
        return "cow"

def fast(cache, key):
    if key in cache:
        return cache[key]
    else:
        return None

def add_to_cache(cache, value, key):
    cache[key] = value
    
