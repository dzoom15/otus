package exceptions

import model.WeakConCommand
class UnknownCommandClass(cmd: WeakConCommand) : RuntimeException("Command $cmd is unknown")